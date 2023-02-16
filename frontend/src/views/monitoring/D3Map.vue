<template>
  <div
      id='container'
      style="
        overflow: hidden;
        position: relative;
        text-align: left;
        touch-action: none;
        width: 100%;
        height: 100%;
        background-color: rgb(235, 235, 235);"
      class='container'
      ref='container'
      @mousemove="updateCoordinates"
  >
    <threat-bar
        style="position: absolute; top: 90%; left: 3%; z-index: 15;"
    ></threat-bar>
    <div
        id="panel"
        v-show="isMouseOver"
        ref="panel"
        :style="{
        position: 'fixed',
        top: mouseY + 'px',
        left: mouseX + 'px',
        zIndex: 0,
        color: 'black',
        backgroundColor: 'rgba(255, 255, 255, 0.9)',
        padding: '1vh',
        font: 'SpoqaHanSansNeo-Bold',
        }"
    >
      <h2>{{ $t('monitoring.vSOCInformationPanel') }}</h2>
      <h4>{{ currentData.region }}</h4>
      <div style="display: flex; flex-direction: column">
        <div style="display: flex; flex-direction: row; flex: 1;">
          <span style="color: #ea5051; text-align: left; flex-grow: 1;"
          >{{ $t('monitoring.danger') }}</span>
          <span style="text-align: right; flex-grow: 1">{{
              currentData.danger
            }}</span>
        </div>
        <div style="display: flex; flex-direction: row; flex: 1;">
          <span style="color: #ea9150; text-align: left; flex-grow: 1"
          >{{ $t('monitoring.warning') }}</span
          >
          <span style="text-align: right; flex-grow: 1">{{
              currentData.warning
            }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import ThreatBar from './ThreatBar'
import Gradient from 'javascript-color-gradient';
import faker from 'faker';
import * as d3 from 'd3';

const MAP_GEOJSON = require('./china.json');

export default {
  name: 'D3Map',
  components: {ThreatBar},
  data: function() {
    return {
      isMouseOver: false,
      mouseY: 0,
      mouseX: 0,
      map: null,
      isMouseInbound: false,
      currentData: {
        region: '',
        threat: 0,
        danger: 0,
        warning: 0,
      },
      regionList:[],
    };
  },
  mounted() {
    this.drawMap();
  },
  methods: {
    drawMap() {
      const _this = this;
      const geojson = MAP_GEOJSON;

      // 현재의 브라우저의 크기 계산
      const divWidth = document.getElementById('container').clientWidth;
      const width = (divWidth < 1000) ? divWidth * 0.9 : 1000;
      const height = width * 1;

      // 지도를 그리기 위한 svg 생성
      const svg = d3
        .select('.container')
        .append('svg')
        .attr('width', '100%')
        .attr('height', '100%');

      // 지도가 그려지는 그래픽 노드(g) 생성
      const g = svg.append('g');
      // 지도가 그려지는 그래픽 노드
      const mapLayer = g.append('g').classed('map-layer', true);

      // 지도의 출력 방법을 선택(메로카토르)
      let projection = d3.geoMercator()
        .scale(1)
        .translate([0, 0])
        .center([74, 56]);

      // svg 그림의 크기에 따라 출력될 지도의 크기를 다시 계산
      const path = d3.geoPath().projection(projection);

      const bounds = path.bounds(geojson);
      const widthScale = (bounds[1][0] - bounds[0][0]) / width;
      const heightScale = (bounds[1][1] - bounds[0][1]) / height;
      const scale = 0.95 / Math.max(widthScale, heightScale);
      projection.scale(scale);

      function fillFn(d){
        const property = d3.select(this)._groups[0].lastItem.__data__.properties;
        if (!property.fakeData) {
          // make fake data
          const colorGradient = new Gradient();
          colorGradient.setMidpoint(101);
          colorGradient.setGradient('#6f9dd4', '#c2dfed', '#faf6c4', '#cd5c58');

          const threat = faker.datatype.number(100);
          const danger = threat * 100;
          const warning = threat * 200;
          const color = colorGradient.getArray()[threat];
          const fakeData = {
            threat: threat,
            danger: danger,
            warning: warning,
            color: color
          };

          d3.select(this)._groups[0].lastItem.__data__.properties.fakeData = fakeData;
        }
        return property.fakeData.color;
      }

      function mouseover(d) {
        _this.isMouseOver = true;
        d3.select(this).attr('stroke-width', 2);
        if (d) {
          const property = d3.select(this)._groups[0].lastItem.__data__.properties;
          _this.currentData.region = property.name;
          _this.currentData.threat = property.fakeData.threat;
          _this.currentData.danger = property.fakeData.danger;
          _this.currentData.warning = property.fakeData.warning;
        }
      }

      function mouseout(d) {
        _this.isMouseOver = false;
        d3.select(this).attr('stroke-width', 0);
      }

      const zoomed = ({transform}) => {
        mapLayer.attr('transform', transform);
      }
      const zoom = d3.zoom().on('zoom', zoomed);
      svg.call(zoom);

      // 지도 그리기
      mapLayer
        .selectAll('path')
        .data(geojson.features)
        .enter().append('path')
        .attr('d', path)
        .attr('vector-effect', 'non-scaling-stroke')
        .attr('stroke', 'gray')
        .attr('stroke-width', 0)
        .style('fill', fillFn)
        .on('mouseover', mouseover)
        .on('mouseout', mouseout);
    },
    updateCoordinates(event) {
      this.mouseX = event.clientX - this.$refs.panel.clientWidth / 2;
      this.mouseY = event.clientY - this.$refs.panel.clientHeight - 10;
    },
  }
}
</script>

<style scoped>
canvas {
  position: relative !important;
}
</style>
