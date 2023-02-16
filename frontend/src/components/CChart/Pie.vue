<script>
import {Pie} from 'vue-chartjs';

export default {
  extends: Pie,
  props: {
    // chartData properties
    labels: { // data labels
      type: Array,
      default: () => []
    },
    tooltips: {
      type: Object,
      default: () => new Object({
        bodyFontSize: 20,
        bodyFontStyle: "bold",
        backgroundColor: '#ffffff',
        bodyFontColor: "#000000",
        borderWidth:3,
        borderColor: 'rgba(0, 0, 0, 0.5)',
      })
    },
    datasets: { // chart data array
      type: Array,
      default: () => []
    },

    legend: {
      type: Object,
      default: () => new Object({
        display: true,
        align: 'center', // [start, center(default), end]
        position: 'top', // [top(default) | left | bottom | right]

        //legend label configuration
        labels: {
          boxWidth: 40, //default 40
          fontSize: 12, //default 12
        },
      })
    },
    title: {
      type: Object,
      default:() => ({
            display: false,
          })
    }
  },
  computed: {
    chartData() {
      return ({
        labels: this.labels,
        datasets: this.datasets
      });
    },
    options() {
      return ({
        // 폰트 크기 미적용 문제로 일단 주석 처리
        legend: this.legend,
        tooltips: this.tooltips,
        responsive: true,
        maintainAspectRatio: false,
        title:this.title
      });
    },
  },
  watch: {
    datasets() {
      this.changeDatasetsColors();
      this.renderChart(this.chartData, this.options);
    },
    title() {
      this.renderChart(this.chartData, this.options);
    }
  },
  methods: {
    changeDatasetsColors() {
      let datasets = this.datasets;
      for (let idx in datasets) {
        for (let cIdx in datasets[idx]['backgroundColor']) {
          let color = datasets[idx]['backgroundColor'][cIdx];
          datasets[idx]['backgroundColor'][cIdx] = this.$vuetify.theme.themes.light[color] || color;
        }
      }
    }
  },
  created() {
  },
  mounted() {
    this.changeDatasetsColors();
    this.renderChart(this.chartData, this.options);
  }
}
</script>
