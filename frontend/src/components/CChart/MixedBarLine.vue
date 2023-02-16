
<script>
import { Bar } from 'vue-chartjs';

export default {
  name: 'MixedChart',
  extends: Bar,
  props: {
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
    legend: { // legend properties
      type: Object,
      default: () => new Object({
        display: true,
        align: 'center', // [start, center(default), end]
        position: 'top', // [top(default) | left | bottom | right]

        //legend label configuration
        labels: {
          boxWidth: 16, // default 40
        },
      })
    },
    scales: { // yAxes, xAxes properties
      type: Object,
      default: () => new Object({
        yAxes: [{
          ticks: {
            beginAtZero: true,
            stepSize: 200,
          },
          gridLines: {
            display: true,
            drawBorder: false,
          }
        }],
        xAxes: [ {
          gridLines: {
            display: false
          },
        }]
      })
    },
  },
  mounted() {
    this.renderChart(this.chartData, this.options);
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
        // tooltips: this.tooltips,
        hover: this.hover,
        animation: this.animation,
        responsive: true,
        maintainAspectRatio: false,
      });
    },
  },
  watch: {
    datasets() {
      this.renderChart(this.chartData, this.options);
    }
  },
};
</script>