<script>
import {Bar} from 'vue-chartjs';
export default {
  extends: Bar,
  props: {
    // chartData properties
    labels: { // x Axes labels
      type: Array,
      default: () => []
    },
    datasets: { // chart data array
      type: Array,
      default: () => []
    },
    hover: {
      type: Object,
      default: () => new Object({})
    },
    animation: {
      type: Object,
      default: () => new Object({})
    },
    // options properties
    scales: { // yAxes, xAxes properties
      type: Object,
      default: () => new Object({
        yAxes: [{
          ticks: {
            beginAtZero: true,
            fontSize: 40,
          },
          gridLines: {
            display: true
          }
        }],
        xAxes: [ {
          gridLines: {
            display: false
          },
          ticks: {
            fontSize: 35,
          },
        }]
      })
    },
    tooltips: {
      type: Object,
      default: () => new Object({
        titleFontSize: 12,
        bodyFontSize: 12,
        backgroundColor: '#ffffff',
        titleFontColor:'#000000',
        bodyFontColor: '#000000',
        borderWidth:3,
        borderColor: 'rgba(0, 0, 0, 0.5)',
      })
    },
    legend: { // legend properties
      type: Object,
      default: () => new Object({
        display: true,
        align: 'center', // [start, center(default), end]
        position: 'top', // [top(default) | left | bottom | right]

        //legend label configuration
        labels: {
          boxWidth: 40, //default 40
          fontSize: 40, //default 12
        },
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
        scales: this.scales,
        legend: this.legend,
        tooltips: this.tooltips,
        hover: this.hover,
        animation: this.animation,
        responsive: true,
        maintainAspectRatio: false,
      });
    },
  },
  watch: {
    datasets() {
      this.changeDatasetsColors();
      this.renderChart(this.chartData, this.options);
    }
  },
  methods: {
    changeDatasetsColors() {
      let datasets = this.datasets;
      for(let idx in datasets) {
        for(let key in datasets[idx]) {
          if(key.indexOf("color") > -1 || key.indexOf("Color") > -1) {
            const themeColor = this.$vuetify.theme.themes.light[datasets[idx][key]];
            datasets[idx][key] =  themeColor || datasets[idx][key];
          }
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
