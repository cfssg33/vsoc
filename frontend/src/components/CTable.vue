<template>
  <div class="text-center">
    <v-data-table
      v-bind="{...$props, ...$attrs}"
      :headers="headers"
      :items="items"
      :show-expand="showExpand"
      v-model="selectedRows"
      class="elevation-1"
      @click:row="onClickRow($event)"
    >
      <template v-for="reqHeader in requiredHeaders" v-slot:[`header.${reqHeader.value}`]="{ header }">
        <span class="red--text">*</span>
        {{ reqHeader.text }}
      </template>

      <template v-for="header in getItemSlotHeaders" v-slot:[`item.${header.value}`]="{ item }">
        <slot :name="header.value" :item="item">
          {{ item[header.value] }}
        </slot>
      </template>

      <template #expanded-item="{ item, headers }">
        <td :colspan="headers.length">
          <slot name="expanded" :item="item">
          </slot>
        </td>
      </template>
    </v-data-table>
  </div>
</template>
<script>
export default {
  props: {
    headers: { // table header columns
      type: Array,
      //properties
      // {
      //     text: string // describe data
      //     value: string // header value (key)
      //     align?: 'start' | 'center' | 'end'
      //     sortable?: boolean
      //     filterable?: boolean
      //     divider?: boolean
      //     class?: string | string[]
      //     width?: string | number
      //     filter?: (value: any, search: string, item: any) => boolean
      //     sort?: (a: any, b: any) => number
      // }
    },
    items: { // items
      type: Array,
    },
    showExpand: { // Shows the expand
      type: Boolean,
      default: false
    },
    selected: { // selected rows, bind with selectedRows
      type: Array,
      default: () => []
    },
  },
  data: () => {
    return {
      selectedRows: [],
      page: 1
    }
  },
  created() {
    if(this.showExpand) {
      this.headers.push({text: '', value: 'data-table-expand'})
    }
  },
  methods: {
    onClickRow: function(e) {
      this.$emit('click', e)
    }
  },
  computed: {
    getItemSlotHeaders: function() {
      return this.showExpand ? this.headers.slice(0, this.headers.length-1) : this.headers
    },
    requiredHeaders: function() {
      return this.headers.reduce((acc, cur) => {
        if(!!cur.required) {
          acc[cur.value] = cur;
        }
        return acc;
      }, {});
    }
  },
  watch: {
    selectedRows: function(value) {
      this.$emit('update:selected', value)
    },
    selected(newVal) {
      this.selectedRows = newVal;
    }
  }
}
</script>
<style lang="scss" scoped>
  .v-data-footer__select {
    flex-basis: auto;

    .v-select {
      flex-basis: 50px;

      .v-select__selections {
        flex-basis: auto;
      }
    }
  }

  .v-list-item__content {
    flex: 1 1 auto;
    min-width: 40px;
  }
</style>
