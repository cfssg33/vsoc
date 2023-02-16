<script>
//Importing Line class from the vue-chartjs wrapper
import {Line} from 'vue-chartjs'
//Exporting this so it can be used in other components
export default {
  extends: Line,
  props: {
    // chartData properties
    labels: { // x Axes labels
      type: Array,
      default: () => []
    },
    datasets: { // chart data array
      type: Array,
      default: () => []

      // dataset properties
      // {
      //     label: String
      //     backgroundColor: String
      //     data: Array
      // }
    },

    // options properties
    scales: { // yAxes, xAxes properties
      type: Object,
    },
    legend: { // legend properties
      type: Object
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
        scales: this.scales
            ||
            {
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
            },
        // 폰트 크기 미적용 문제로 일단 주석 처리
        // legend: this.legend
        //     ||
        //     {
        //       display: true,
        //       align: 'center', // [start, center(default), end]
        //       position: 'top', // [top(default) | left | bottom | right]

        //       //legend label configuration
        //       labels: {
        //         boxWidth: 40, //default 40
        //         fontSize: 40, //default 12
        //       },
        //     },
        responsive: true,
        maintainAspectRatio: false
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
