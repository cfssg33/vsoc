<template>
  <div>
    <div class="d-flex flex-row">
      <div class="threatBarHelp">{{ $t('monitoring.safe') }}</div>
      <div class="threatBarHelp">{{ $t('monitoring.moderate') }}</div>
      <div class="threatBarHelp">{{ $t('monitoring.severe') }}</div>
      <div class="threatBarHelp">{{ $t('monitoring.critical') }}</div>
    </div>
    <div class="threatBar"></div>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
.threatBarHelp {
  position: relative;
  color: white;
  z-index: 16;
  text-align: center;
  font: SpoqaHanSansNeo;
  font-size: 1.3rem;
  flex-grow: 1;
  text-shadow: 2px 2px 2px black;
}
.threatBar {
  position: relative;
  width: 20vw;
  height: 2vh;
  border-radius: 0.3vw;
  background: linear-gradient(to right, #6f9dd4, #c2dfed, #faf6c4, #cd5c58);
  z-index: 15;
}
</style>
