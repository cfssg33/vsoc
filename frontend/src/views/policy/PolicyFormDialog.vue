<template>
  <v-dialog v-model="dialogState" max-width="500">
    <template #activator="{ on, attrs }">
      <slot name="activator" :on="on" :attrs="attrs">
        <v-btn color="mainBlue" class="white--text trigger-button font-weight-bold" v-on="on" v-bind="dialog.trigger.attrs">
          <v-icon v-if="dialog.trigger.icon" size="medium">
            {{ dialog.trigger.icon }}
          </v-icon>
          {{ dialog.trigger.title }}
        </v-btn>
      </slot>
    </template>
    <v-card>
      <v-form v-model="validState" class="white">
        <v-card-title>{{ dialog.title }}</v-card-title>
        <v-card-text class="pb-0">
          <v-container>
            <v-row class="d-flex flex-column" no-gutters>
              <v-col v-for="input in dialog.inputs" :key="input.key">
                <component :is="input.component" :label="input.label" v-model="input.value" v-bind="input.attrs"></component>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions class="d-flex justify-center align-content-space-between pb-5">
          <v-btn class="action-button white--text font-weight-bold mx-5" width="130" large color="mainBlue" @click="submitDialog" :disabled="!validState">{{ dialog.submit.title }}</v-btn>
          <v-btn class="action-button white--text font-weight-bold mx-5" width="130" large color="buttonGrey" @click="dialogState=false">{{ dialog.submit.cancel }}</v-btn>
        </v-card-actions>
      </v-form>
    </v-card>
  </v-dialog>
</template>

<script>
import {VFileInput, VTextField} from 'vuetify/lib';
import eventBus from "@/eventBus";

export default {
  name: 'PolicyFormDialog',
  components: { VTextField, VFileInput },
  data() {
    return {
      validState: false,
      dialogState: false,
    }
  },
  props: {
    dialog: {
      type: Object,
      required: true,
      default: () => (new Object({
        title: 'Dialog Title',
        inputs: [
          {
            component: 'v-text-field',
            label: 'input Title',
            key: 'someKey',
            value: '',
            attrs: {
              rules: [
                value => !!value || 'Value is Required!',
                value => (value && value.length <= 10) || 'value must be less than 10 characters',
              ]
            }
          }
        ],
        trigger: { title: 'Click Me', icon: 'mdi-plus' },
        submit: {
          title: 'Submit Button Title',
          cancel: 'Cancel Button Title',
          action: () => console.log('submit executed') }
      }))
    }
  },
  methods: {
    formClean() {
      this.dialog.inputs.forEach(input => {
        if (input.component === 'v-file-input') {
          input.value = [];
        } else if (input.component === 'v-text-field') {
          input.value = '';
        }
      });
    },

    async submitDialog() {
      try {

        const formData = new FormData();
        formData.append('policyFile', this.dialog.inputs[0].value);
        formData.append('policyName', this.dialog.inputs[1].value);
        formData.append('policyVersion', this.dialog.inputs[2].value);
        formData.append('userName', this.$store.state.userInfo.userId);

        let type = this.dialog.inputs[3].value;

        this.items = await this.$api.addPolicy(formData, type);

        this.formClean();
        this.dialogState = false;

        if (type === 'host') {
          eventBus.$emit('getPolicyHostIdpsData');
        }else if (type === 'can') {
          eventBus.$emit('getPolicyCanIdsData');
        }

      } catch (e) {
        //console.error(e);
      }
    }
  }
}
</script>

<style scoped>
.trigger-button {
  font-size: 14px;
  text-transform: none;
}

.action-button {
  font-size: 14px;
  text-transform: none;
}
</style>