import Vue from 'vue';
import VueRouter from 'vue-router';
import vsocRouteInfo from './vsoc';

Vue.use(VueRouter);

const totalRouteInfo = [...vsocRouteInfo];

const routes = totalRouteInfo.flatMap(routeInfo => {
  return (routeInfo.type === 'category') ? routeInfo.subRoutes : routeInfo;
});

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
});

export default router;
