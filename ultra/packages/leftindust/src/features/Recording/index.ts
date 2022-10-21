import { AudioStream, TranscribeStreamingClient } from '@aws-sdk/client-transcribe-streaming';
import { config } from '../App';
import { Buffer } from 'buffer';

import Microphone from 'microphone-stream';

export type RecordingTranscript = { time: string; text: string }[];

export const detectRecordingDevice = (devices: MediaDeviceInfo[]): { id: string, value: string }[] => {
  let newDevices: { id: string, value: string }[] = [];
  for (let i = 0; i !== devices.length; i++) {
    const deviceInfo = devices[i];
    
    if (deviceInfo.kind === 'audioinput') {
      newDevices = [...newDevices, {
        id: deviceInfo.deviceId,
        value: deviceInfo.label || 'Microphone ' + (newDevices.length + 1),
      }];
    }
  }
  
  return newDevices;
};

export const convertBlock = (buffer: ArrayBuffer): Float32Array => {
  const incomingData = new Uint8Array(buffer);
  let i: number;
  const l = incomingData.length; // length, we need this for the loop
  const outputData = new Float32Array(incomingData.length); // create the Float32Array for output
  for (i = 0; i < l; i++) {
    outputData[i] = (incomingData[i] - 128) / 128.0; // convert audio to float
  }
  return outputData; // return the Float32Array
};

export const pcmEncodeChunk = (chunk: Float32Array) => {
  let offset = 0;
  const buffer = new ArrayBuffer(chunk.length * 2);
  const view = new DataView(buffer);
  for (let i = 0; i < chunk.length; i++, offset += 2) {
    
    const s = Math.max(-1, Math.min(1, chunk[i]));
    view.setInt16(offset, s < 0 ? s * 0x8000 : s * 0x7fff, true);
  }
  
  return Buffer.from(buffer);
};

export const audioStream = async function* (stream: Microphone): AsyncIterable<AudioStream> {
  let exhausted = false;

  const onData = async (): Promise<AudioStream> =>
    new Promise((resolve) => {
      stream.once('data', (chunk: Uint8Array) => {
        resolve({
          AudioEvent: {
            AudioChunk: pcmEncodeChunk(Microphone.toRaw(chunk)),
          },
        });
      });
    });
  
  try {
    while (true) {
      const chunk = await onData();

      if (chunk == undefined) {
        exhausted = true;
        break;
      }
        
      yield chunk;
    }
  } finally {
    if (!exhausted) {
      stream.destroy();
    }
  }
};

export const awsTranscribeClient = new TranscribeStreamingClient({
  region: 'us-east-1',
  credentials: {
    accessKeyId: config.aws.accessKeyId,
    secretAccessKey: config.aws.secretAccessKey,
  },
});

export const animateIncomingText = (node: HTMLElement, params?: { delay: number, duration: number, easing: number }) => {
  const { duration, delay, easing } = params || {};
  const { color } = window.getComputedStyle(node);
  return {
    duration,
    delay,
    easing,
    css(t: number) {
      const percentage = t * 200;

      return `opacity: ${percentage}%`;
    },
  };
};