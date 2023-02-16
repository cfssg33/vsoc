import DetectionCanIds from '@/views/detectionLog/DetectionCanIds';
import DetectionHostIdps from '@/views/detectionLog/DetectionHostIdps';
import VehiclePlatformList from '../views/vehicle/VehiclePlatformList';
import VehiclePlatformDetail from '../views/vehicle/VehiclePlatformDetail';
import VehicleList from '../views/vehicle/VehicleList';
import VehicleDetails from '../views/vehicle/VehicleDetails';
import Monitoring from '../views/monitoring/Monitoring';
import Login from '../views/login/Login';
import PolicyCanIds from '../views/policy/PolicyCanIds';
import PolicyDetail from '../views/policy/PolicyDetail';
import HistoryLogDetail from '../views/policy/HistoryLogDetail';
import PolicyEdit from '../views/policy/PolicyEdit';
import PolicyHostIdps from '../views/policy/PolicyHostIdps';
import TrendsAnalytics from '../views/main/TrendsAnalytics';
import Account from '../views/account/AccountList'
import ApiLogList from '../views/apiLog/ApiLogList';

const vsocRouteInfo = [
  {
    type: 'hidden',
    name: 'Login',
    path: '/login',
    component: Login
  },
  {
    type: 'category',
    name: 'main',
    subRoutes: [
      {
        type: 'menu',
        name: 'monitoring',
        path: '/monitoring',
        component: Monitoring
      },
      {
        type: 'menu',
        name: 'trendAnalytics',
        path: '/trends',
        component: TrendsAnalytics
      }
    ]
  },
  {
    type: 'category',
    name: 'detectionLog',
    subRoutes: [
      {
        type: 'menu',
        name: 'detectionCanIds',
        path: '/detection/can',
        component: DetectionCanIds
      },
      {
        type: 'menu',
        name: 'detectionHostIdps',
        path: '/detection/host',
        component: DetectionHostIdps
      }
    ]
  },
  {
    type: 'category',
    name: 'policyManagement',
    subRoutes: [
      {
        type: 'menu',
        name: 'policyCanIds',
        path: '/policy/can',
        component: PolicyCanIds
      },
      {
        type: 'menu',
        name: 'policyHostIdps',
        path: '/policy/host',
        component: PolicyHostIdps
      },
      {
        type: 'hidden',
        name: 'policyDetail',
        path: '/policy/:policyType/:policyId',
        component: PolicyDetail,
        props: true
      },
      {
        type: 'hidden',
        name: 'policyHistoryLogDetail',
        path: '/policy/:policyType/:policyId/history/:historyId',
        component: HistoryLogDetail,
        props: true
      },
      {
        type: 'hidden',
        name: 'policyEdit',
        path: '/policy/:policyType/:policyId/edit/:version',
        component: PolicyEdit,
        props: true
      }
    ]
  },
  {
    type: 'category',
    name: 'vehicleManagement',
    subRoutes: [
      {
        type: 'menu',
        name: 'vehicleList',
        path: '/vehicles',
        component: VehicleList
      },
      // {
      //   type: 'menu',
      //   name: 'vehiclePlatformList',
      //   path: '/vehicle-platforms',
      //   component: VehiclePlatformList
      // },
      {
        type: 'hidden',
        name: 'vehicleDetails',
        path: '/vehicles/details',
        component: VehicleDetails,
      },
      {
        type: 'hidden',
        name: 'vehiclePlatformDetails',
        path: '/vehicle-platforms/:platformId',
        component: VehiclePlatformDetail,
        props: true
      }
    ]
  },
  {
    type: 'category',
    name: 'systemSettings',
    subRoutes: [
      // {
      //   type: 'menu',
      //   name: 'settings',
      //   path: '/setting'
      // },
      {
        type: 'menu',
        name: 'account',
        path: '/account',
        component: Account
      },
      {
        type: 'menu',
        name: 'apiLog',
        path: '/log',
        component: ApiLogList
      }
    ]
  }
];

export default vsocRouteInfo;
