<script lang="ts">
  import type { Selectable } from '@framework/modules/SelectModule';
  import type { DoctorIdInput, GroupIdInput, UserInput } from '@framework/schemas/leftindust.schema';

  import type { Popover } from 'framework7/types';

  import { PluginTypes } from '@framework/plugins';

  import { DEFAULT_RANGE_INPUT } from '@framework/requests';
  import language from '@framework/languages';
  import { writable } from 'svelte/store';
  import { getNativeAPI } from '@framework/bridge';

  import GroupsGenericEngine from '@framework/engines/groups/GroupsGenericEngine';
  import UserMutateEngine from '@framework/engines/users/UserMutateEngine';
  
  import { Row, Col, Block } from 'framework7-svelte';
  
  import { closeWizard, openPopover } from '@framework/modules/NavigationModule';
  import { UsersSelectedTab, UsersTab } from '@/pages/users/store/UsersStore';

  import InputUI from '@framework/ui/input/InputUI/InputUI.svelte';
  import WizardUI from '@framework/ui/layout/WizardUI/WizardUI.svelte';
  import SelectUI from '@framework/ui/input/SelectUI/SelectUI.svelte';
  import AddUI from '@framework/ui/input/AddUI/AddUI.svelte';

  import SpecificPluginGrid from '@framework/components/grid/SpecificPluginGrid.svelte';
  import AttachmentsModal from '@framework/components/modal/AttachmentsModal.svelte';

  const { Dialog } = getNativeAPI();

  let userPopover: Popover.Popover;
  let doctorPopover: Popover.Popover;

  export let reference: Selectable<'User'> | undefined = undefined;

  const { groups } = GroupsGenericEngine({ range: DEFAULT_RANGE_INPUT });

  let user = writable<Selectable[]>(reference ? [reference] : []);
  let doctor = writable<Selectable[]>([]);

  let input: UserInput & {
    group: NonNullable<GroupIdInput>;
    doctor: NonNullable<DoctorIdInput>;
  } = {
    nameInfo: {
      firstName: '',
      middleName: '',
      lastName: '',
    },
    group: {
      id: undefined,
    },
    doctor: {
      id: undefined,
    },
    uid: reference?.id ?? '',
  };

  $: disabled = !(input.nameInfo.firstName
    && input.nameInfo.lastName
    && input.group?.id
    && input.uid
  );

  const submit = () => {
    void UserMutateEngine(<UserInput>input).then((result) => {
      result ? (() => {
        closeWizard();
        $UsersSelectedTab = UsersTab.Registered;
      })() : void Dialog.alert({
        message: language().errors.internalError.text,
        detail: language().errors.request.text,
        buttons: [language().buttons.ok.text],
      });
    });
  };

  $: if ($user[0]) input.uid = $user[0].id;
  $: if ($doctor[0]) input.doctor.id = $doctor[0].id;

  $: console.log(input);
</script>

<AttachmentsModal
  attachments={user}
  types={['User']}
  bind:instance={userPopover}
  multiple={false}
  props={{
    filterRegistered: true,
  }}
/>

<AttachmentsModal
  attachments={doctor}
  types={['Doctor']}
  bind:instance={doctorPopover}
  multiple={false}
/>

<WizardUI
  title={language().user.headers.newUser.text}
  subtitle={language().user.placeholders.addUserDescription.text}
  color="blue"
  {disabled}
  on:submit={submit}
>
  <Block style="margin-top: 60px">
    <Block>
      <h4>Identification</h4>
      <Row>
        <Col xlarge="50" width="100">
          <Row>
            <Col width="100" medium="33">
              <InputUI>
                <input type="text" placeholder="First Name" bind:value={input.nameInfo.firstName} />
              </InputUI>
              <p />
            </Col>
            <Col width="100" medium="33">
              <InputUI>
                <input type="text" placeholder="Middle Name (Optional)" bind:value={input.nameInfo.middleName} />
              </InputUI>
              <p />
            </Col>
            <Col width="100" medium="33">
              <InputUI>
                <input type="text" placeholder="Last Name" bind:value={input.nameInfo.lastName} />
              </InputUI>
              <p />
            </Col>
          </Row>
        </Col>
        <Col xlarge="50" width="100">
          <Row>
            <Col width="100">
              <SelectUI
                title="Group"
                disabled={!$groups}
                options={$groups.map((group) => ({
                  text: group.name,
                  value: group.gid.id,
                }))}
                bind:value={input.group.id}
              />
              <p />
            </Col>
          </Row>
        </Col>
        <Col width="100">
          {#if $user.length > 0}
            {#key $user}
              <SpecificPluginGrid
                props={$user.map((selectable) => {
                  if (selectable.type) {
                    return {
                      id: selectable.type,
                      selectable: {
                        id: selectable.id,
                        type: selectable.type,
                      },
                      attachments: user,
                    };
                  }
                })}
                type={PluginTypes.Widget}
                dynamicGap
              />
            {/key}
          {/if}
          <AddUI title="Attach User" on:click={(event) => openPopover(userPopover, event)} />
          <p />
        </Col>
        <Col width="100">
          {#if $doctor.length > 0}
            {#key $doctor}
              <SpecificPluginGrid
                props={$doctor.map((selectable) => {
                  if (selectable.type) {
                    return {
                      id: selectable.type,
                      selectable: {
                        id: selectable.id,
                        type: selectable.type,
                      },
                      attachments: doctor,
                    };
                  }
                })}
                type={PluginTypes.Widget}
                dynamicGap
              />
            {/key}
          {/if}
          <AddUI title="Attach Doctor (Optional)" on:click={(event) => openPopover(doctorPopover, event)} />
          <p />
        </Col>
      </Row>
    </Block>
  </Block>
</WizardUI>