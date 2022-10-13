import { TranscribeStreamingClient } from '@aws-sdk/client-transcribe-streaming';
import { config } from '../App';

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

export const awsTranscribeClient = new TranscribeStreamingClient({
  region: 'us-east-1',
  credentials: {
    accessKeyId: config.aws.accessKeyId,
    secretAccessKey: config.aws.secretAccessKey,
  },
});