<script lang="ts">
  import { Segmented, Button, f7 } from 'framework7-svelte';

  import { format } from 'date-fns';

  import InputUI from '../InputUI/InputUI.svelte';

  export let value: number = new Date().getTime();
  $: date = new Date(value);
  
  $: hours = (() => {
    let h = date?.getHours();
    h = h % 12;
    return h ? h : 12;
  })().toString();
  $: minutes = (date.getMinutes() <= 10) ? `0${date.getMinutes()}` : date?.getMinutes().toString();
  $: ampm = (date.getHours() && date.getHours() >= 12 ? 'pm' : 'am') ?? 'am';

  const getTimeAsDate = () => {
    let newValue = new Date(`${format(date, 'yyyy-MM-dd')} ${hours}:${minutes} ${ampm}`).getTime();
    if (!isNaN(newValue)) value = newValue;
  };

  $: if (hours && minutes && ampm) {
    getTimeAsDate();
  }

</script>

<div class="ultra-time">
  <div class="ultra-time-input">
    <InputUI>
      <div class="ultra-time-input-container" slot="content">
        <div class="ultra-time-input-wrapper">
          <input type="number" min={0} value={hours} on:input={(event) => {
            let value = parseInt(event.currentTarget?.value);

            if (parseInt(event.currentTarget?.value.charAt(1)) && parseInt(event.currentTarget?.value.charAt(0)) !== 1) {
                hours = event.currentTarget?.value.charAt(1);
            } else if (value > 12) {
              if (value > 100) {
                if (
                  parseInt(event.currentTarget?.value.slice(0, 2)) >= 10
                  && parseInt(event.currentTarget?.value.slice(0, 2)) <= 12
                ) {
                  hours = event.currentTarget?.value.charAt(2);
                } else {
                  hours = event.currentTarget?.value.slice(0, 2);
                }
              } else {
                if (parseInt(event.currentTarget?.value.charAt(1)) > 2) {
                  hours = event.currentTarget?.value.charAt(1);
                } else {
                  hours = event.currentTarget?.value.charAt(0);
                }
              }
            } else {
              hours = event.currentTarget?.value;
            }

            if (value === 0 ?? !event.currentTarget?.value) {
              hours = '1';
            }

            // Add manual binding because binding with svelte with the "bind:value" keyword
            // causes reactivity issues in this component
            event.target && f7.$(event.target).val(hours);
          }} />
        </div>
        <div class="ultra-time-input-wrapper-separator">:</div>
        <div class="ultra-time-input-wrapper">
          <input type="number" min={0} value={minutes} on:input={(event) => {
            let value = parseInt(event.currentTarget?.value);

            if (value > 59) {
              minutes = event.currentTarget?.value.slice(0, 2);
            } else if (value < 10 && value !== 0 && event.currentTarget?.value.length < 2) {
              minutes = `0${event.currentTarget?.value.charAt(0)}`;
            } else {
              if (parseInt(event.currentTarget?.value.charAt(0)) === 0) {
                if (value === 0) {
                  minutes = '00';
                } else {
                  minutes = event.currentTarget?.value.slice(1, 3);
                }
              } else {
                minutes = event.currentTarget?.value;
              }
            }

            // Referenced above
            event.target && f7.$(event.target).val(hours);
          }} />
        </div>  
        <div class="ultra-time-input-wrapper-ampm">
          <Segmented strong tag="p">
            <Button active={ampm === 'am'} on:click={() => ampm = 'am'}>AM</Button>
            <Button active={ampm === 'pm'} on:click={() => ampm = 'pm'}>PM</Button>
          </Segmented>
        </div>
      </div>
    </InputUI>
  </div>
</div>
