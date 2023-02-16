<template>
  <v-card>
    <v-row no-gutters>
      <v-col cols="5" class="d-flex flex-row">
        <v-col cols="6" class="font-weight-bold">
          {{ $t('vehicleDetails.detailInfoComponent.vehicleId') }}
        </v-col>
        <v-col cols="6" class="infoGrey--text">
          {{ infoData.vehicleId }}
        </v-col>
      </v-col>
      <v-col cols="7"></v-col>
      <v-col cols="5" class="d-flex flex-row">
        <v-col cols="6" class="font-weight-bold">
          {{ $t('vehicleDetails.detailInfoComponent.brand') }}
        </v-col>
        <v-col cols="6" class="infoGrey--text">
          {{ infoData.brand }}
        </v-col>
      </v-col>
      <v-col cols="7"></v-col>
      <v-col cols="5" class="d-flex flex-row">
        <v-col cols="6" class="font-weight-bold">
          {{ $t('vehicleDetails.detailInfoComponent.type') }}
        </v-col>
        <v-col cols="6" class="infoGrey--text">
          {{ infoData.type }}
        </v-col>
      </v-col>
      <v-col cols="5" class="d-flex flex-row">
        <v-col cols="6" class="font-weight-bold">
          {{ $t('vehicleDetails.detailInfoComponent.size') }}
        </v-col>
        <v-col cols="6" class="infoGrey--text">
          {{ infoData.size }}
        </v-col>
      </v-col>
      <v-col cols="5" class="d-flex flex-row">
        <v-col cols="6" class="font-weight-bold">
          {{ $t('vehicleDetails.detailInfoComponent.registeredDate') }}
        </v-col>
        <v-col cols="6" class="infoGrey--text">
          {{ formatDateWithDot(infoData.registeredDate) }}
        </v-col>
      </v-col>
      <v-col cols="5" class="d-flex flex-row">
        <v-col cols="6" class="font-weight-bold">
          {{ $t('vehicleDetails.detailInfoComponent.registeredCountry') }}
        </v-col>
        <v-col cols="6" class="infoGrey--text">
          {{ infoData.registeredCountry }}
        </v-col>
      </v-col>
      <v-col cols="5" class="d-flex flex-row">
        <v-col cols="6" class="font-weight-bold">
          {{ $t('vehicleDetails.detailInfoComponent.lastDate') }}
        </v-col>
        <v-col cols="6" class="infoGrey--text">
          {{ formatDateWithDot(infoData.lastDate) }}
        </v-col>
      </v-col>
      <v-col cols="5" class="d-flex flex-row">
        <v-col cols="6" class="font-weight-bold">
          {{ $t('vehicleDetails.detailInfoComponent.lastCountry') }}
        </v-col>
        <v-col cols="6" class="infoGrey--text">
          {{ infoData.lastCountry }}
        </v-col>
      </v-col>
      <v-col cols="5" class="d-flex flex-row">
        <v-col cols="6" class="font-weight-bold">
          {{ $t('vehicleDetails.detailInfoComponent.policy') }}
        </v-col>
        <v-col cols="6" class="infoGrey--text">
          {{ infoData.policyName }}
        </v-col>
      </v-col>
      <v-col cols="5" class="d-flex flex-row">
        <v-col cols="6" class="font-weight-bold">
          {{ $t('vehicleDetails.detailInfoComponent.version') }}
        </v-col>
        <v-col cols="6" class="infoGrey--text">
          {{ infoData.version }}
        </v-col>
      </v-col>
    </v-row>
  </v-card>
</template>

<script>
import moment from 'moment';

export default {
  name: 'InfoCard',
  props: {
    infoData: {
      type: Object
    }
  },
  methods: {
    formatDateWithDot(dateTimeString) {
      const str = moment(dateTimeString).format('YYYY.MM.DD');
      if (str === 'Invalid date') {
        return '-';
      }
      return str;
    }
  }
}
</script>

<style scoped>

</style>
