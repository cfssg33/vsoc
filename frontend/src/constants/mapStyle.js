const styleJson2 = [
  {
    featureType: 'land',
    elementType: 'geometry',
    stylers: {
      color: '#ffffffff',
    },
  },
  {
    featureType: 'manmade',
    elementType: 'geometry',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'water',
    elementType: 'geometry',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'road',
    elementType: 'geometry.fill',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'road',
    elementType: 'geometry.stroke',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'districtlabel',
    elementType: 'labels.text.fill',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'districtlabel',
    elementType: 'labels.text.stroke',
    stylers: {
      visibility: 'off',
      weight: 3,
    },
  },
  {
    featureType: 'poilabel',
    elementType: 'labels.text.fill',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'poilabel',
    elementType: 'labels.text.stroke',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'subway',
    elementType: 'geometry',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'railway',
    elementType: 'geometry',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'poilabel',
    elementType: 'labels.icon',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'subwaylabel',
    elementType: 'labels',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'subwaylabel',
    elementType: 'labels.icon',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'tertiarywaysign',
    elementType: 'labels',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'tertiarywaysign',
    elementType: 'labels.icon',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'provincialwaysign',
    elementType: 'labels.icon',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'provincialwaysign',
    elementType: 'labels',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'nationalwaysign',
    elementType: 'labels.icon',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'nationalwaysign',
    elementType: 'labels',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'highwaysign',
    elementType: 'labels.icon',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'highwaysign',
    elementType: 'labels',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'green',
    elementType: 'geometry',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'nationalwaysign',
    elementType: 'labels.text.fill',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'nationalwaysign',
    elementType: 'labels.text.stroke',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'city',
    elementType: 'labels',
    stylers: {
      visibility: 'on',
    },
  },
  {
    featureType: 'city',
    elementType: 'labels.icon',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'city',
    elementType: 'labels.text.fill',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'city',
    elementType: 'labels.text.stroke',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'water',
    elementType: 'labels.text.fill',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'water',
    elementType: 'labels.text.stroke',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'local',
    elementType: 'geometry.fill',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'local',
    elementType: 'geometry.stroke',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'fourlevelway',
    elementType: 'geometry.fill',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'fourlevelway',
    elementType: 'geometry.stroke',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'tertiaryway',
    elementType: 'geometry.fill',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'tertiaryway',
    elementType: 'geometry.stroke',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'tertiaryway',
    elementType: 'labels.text.fill',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'fourlevelway',
    elementType: 'labels.text.fill',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'highway',
    elementType: 'labels.text.fill',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'highway',
    elementType: 'geometry.fill',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'highway',
    elementType: 'geometry.stroke',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'provincialway',
    elementType: 'geometry.fill',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'provincialway',
    elementType: 'geometry.stroke',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'tertiaryway',
    elementType: 'labels.text.stroke',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'fourlevelway',
    elementType: 'labels.text.stroke',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'highway',
    elementType: 'labels.text.stroke',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'nationalway',
    elementType: 'labels.text.stroke',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'nationalway',
    elementType: 'labels.text.fill',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'nationalway',
    elementType: 'geometry.fill',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'nationalway',
    elementType: 'geometry.stroke',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'cityhighway',
    elementType: 'geometry.fill',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'cityhighway',
    elementType: 'geometry.stroke',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'arterial',
    elementType: 'geometry.fill',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'arterial',
    elementType: 'geometry.stroke',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'medicallabel',
    elementType: 'labels',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'medicallabel',
    elementType: 'labels.icon',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'entertainmentlabel',
    elementType: 'labels',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'entertainmentlabel',
    elementType: 'labels.icon',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'estatelabel',
    elementType: 'labels',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'estatelabel',
    elementType: 'labels.icon',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'businesstowerlabel',
    elementType: 'labels',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'businesstowerlabel',
    elementType: 'labels.icon',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'companylabel',
    elementType: 'labels',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'companylabel',
    elementType: 'labels.icon',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'governmentlabel',
    elementType: 'labels',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'governmentlabel',
    elementType: 'labels.icon',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'restaurantlabel',
    elementType: 'labels',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'restaurantlabel',
    elementType: 'labels.icon',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'hotellabel',
    elementType: 'labels',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'hotellabel',
    elementType: 'labels.icon',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'shoppinglabel',
    elementType: 'labels',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'shoppinglabel',
    elementType: 'labels.icon',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'lifeservicelabel',
    elementType: 'labels',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'lifeservicelabel',
    elementType: 'labels.icon',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'carservicelabel',
    elementType: 'labels',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'carservicelabel',
    elementType: 'labels.icon',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'financelabel',
    elementType: 'labels',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'financelabel',
    elementType: 'labels.icon',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'otherlabel',
    elementType: 'labels',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'otherlabel',
    elementType: 'labels.icon',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'airportlabel',
    elementType: 'labels',
    stylers: {
      visibility: 'on',
    },
  },
  {
    featureType: 'airportlabel',
    elementType: 'labels.text.fill',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'airportlabel',
    elementType: 'labels.text.stroke',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'airportlabel',
    elementType: 'labels.icon',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'highway',
    stylers: {
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '6',
    },
  },
  {
    featureType: 'highway',
    stylers: {
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '7',
    },
  },
  {
    featureType: 'highway',
    stylers: {
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '8',
    },
  },
  {
    featureType: 'highway',
    stylers: {
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '9',
    },
  },
  {
    featureType: 'highway',
    stylers: {
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '10',
    },
  },
  {
    featureType: 'highway',
    elementType: 'geometry',
    stylers: {
      visibility: 'off',
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '6',
    },
  },
  {
    featureType: 'highway',
    elementType: 'geometry',
    stylers: {
      visibility: 'off',
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '7',
    },
  },
  {
    featureType: 'highway',
    elementType: 'geometry',
    stylers: {
      visibility: 'off',
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '8',
    },
  },
  {
    featureType: 'highway',
    elementType: 'geometry',
    stylers: {
      visibility: 'off',
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '9',
    },
  },
  {
    featureType: 'highway',
    elementType: 'geometry',
    stylers: {
      visibility: 'off',
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '10',
    },
  },
  {
    featureType: 'nationalway',
    stylers: {
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '6',
    },
  },
  {
    featureType: 'nationalway',
    stylers: {
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '7',
    },
  },
  {
    featureType: 'nationalway',
    stylers: {
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '8',
    },
  },
  {
    featureType: 'nationalway',
    stylers: {
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '9',
    },
  },
  {
    featureType: 'nationalway',
    stylers: {
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '10',
    },
  },
  {
    featureType: 'nationalway',
    elementType: 'geometry',
    stylers: {
      visibility: 'off',
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '6',
    },
  },
  {
    featureType: 'nationalway',
    elementType: 'geometry',
    stylers: {
      visibility: 'off',
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '7',
    },
  },
  {
    featureType: 'nationalway',
    elementType: 'geometry',
    stylers: {
      visibility: 'off',
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '8',
    },
  },
  {
    featureType: 'nationalway',
    elementType: 'geometry',
    stylers: {
      visibility: 'off',
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '9',
    },
  },
  {
    featureType: 'nationalway',
    elementType: 'geometry',
    stylers: {
      visibility: 'off',
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '10',
    },
  },
  {
    featureType: 'nationalway',
    elementType: 'labels',
    stylers: {
      visibility: 'off',
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '6',
    },
  },
  {
    featureType: 'nationalway',
    elementType: 'labels',
    stylers: {
      visibility: 'off',
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '7',
    },
  },
  {
    featureType: 'nationalway',
    elementType: 'labels',
    stylers: {
      visibility: 'off',
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '8',
    },
  },
  {
    featureType: 'nationalway',
    elementType: 'labels',
    stylers: {
      visibility: 'off',
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '9',
    },
  },
  {
    featureType: 'nationalway',
    elementType: 'labels',
    stylers: {
      visibility: 'off',
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '10',
    },
  },
  {
    featureType: 'highway',
    elementType: 'labels',
    stylers: {
      visibility: 'off',
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '6',
    },
  },
  {
    featureType: 'highway',
    elementType: 'labels',
    stylers: {
      visibility: 'off',
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '7',
    },
  },
  {
    featureType: 'highway',
    elementType: 'labels',
    stylers: {
      visibility: 'off',
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '8',
    },
  },
  {
    featureType: 'highway',
    elementType: 'labels',
    stylers: {
      visibility: 'off',
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '9',
    },
  },
  {
    featureType: 'highway',
    elementType: 'labels',
    stylers: {
      visibility: 'off',
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '10',
    },
  },
  {
    featureType: 'provincialway',
    stylers: {
      curZoomRegionId: '0',
      curZoomRegion: '8,9',
      level: '8',
    },
  },
  {
    featureType: 'provincialway',
    stylers: {
      curZoomRegionId: '0',
      curZoomRegion: '8,9',
      level: '9',
    },
  },
  {
    featureType: 'provincialway',
    elementType: 'geometry',
    stylers: {
      visibility: 'off',
      curZoomRegionId: '0',
      curZoomRegion: '8,9',
      level: '8',
    },
  },
  {
    featureType: 'provincialway',
    elementType: 'geometry',
    stylers: {
      visibility: 'off',
      curZoomRegionId: '0',
      curZoomRegion: '8,9',
      level: '9',
    },
  },
  {
    featureType: 'provincialway',
    elementType: 'labels',
    stylers: {
      visibility: 'off',
      curZoomRegionId: '0',
      curZoomRegion: '8,9',
      level: '8',
    },
  },
  {
    featureType: 'provincialway',
    elementType: 'labels',
    stylers: {
      visibility: 'off',
      curZoomRegionId: '0',
      curZoomRegion: '8,9',
      level: '9',
    },
  },
  {
    featureType: 'cityhighway',
    stylers: {
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '6',
    },
  },
  {
    featureType: 'cityhighway',
    stylers: {
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '7',
    },
  },
  {
    featureType: 'cityhighway',
    stylers: {
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '8',
    },
  },
  {
    featureType: 'cityhighway',
    stylers: {
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '9',
    },
  },
  {
    featureType: 'cityhighway',
    stylers: {
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '10',
    },
  },
  {
    featureType: 'cityhighway',
    elementType: 'geometry',
    stylers: {
      visibility: 'off',
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '6',
    },
  },
  {
    featureType: 'cityhighway',
    elementType: 'geometry',
    stylers: {
      visibility: 'off',
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '7',
    },
  },
  {
    featureType: 'cityhighway',
    elementType: 'geometry',
    stylers: {
      visibility: 'off',
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '8',
    },
  },
  {
    featureType: 'cityhighway',
    elementType: 'geometry',
    stylers: {
      visibility: 'off',
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '9',
    },
  },
  {
    featureType: 'cityhighway',
    elementType: 'geometry',
    stylers: {
      visibility: 'off',
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '10',
    },
  },
  {
    featureType: 'cityhighway',
    elementType: 'labels',
    stylers: {
      visibility: 'off',
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '6',
    },
  },
  {
    featureType: 'cityhighway',
    elementType: 'labels',
    stylers: {
      visibility: 'off',
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '7',
    },
  },
  {
    featureType: 'cityhighway',
    elementType: 'labels',
    stylers: {
      visibility: 'off',
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '8',
    },
  },
  {
    featureType: 'cityhighway',
    elementType: 'labels',
    stylers: {
      visibility: 'off',
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '9',
    },
  },
  {
    featureType: 'cityhighway',
    elementType: 'labels',
    stylers: {
      visibility: 'off',
      curZoomRegionId: '0',
      curZoomRegion: '6,10',
      level: '10',
    },
  },
  {
    featureType: 'arterial',
    stylers: {
      curZoomRegionId: '0',
      curZoomRegion: '9,10',
      level: '9',
    },
  },
  {
    featureType: 'arterial',
    stylers: {
      curZoomRegionId: '0',
      curZoomRegion: '9,10',
      level: '10',
    },
  },
  {
    featureType: 'arterial',
    elementType: 'geometry',
    stylers: {
      visibility: 'off',
      curZoomRegionId: '0',
      curZoomRegion: '9,10',
      level: '9',
    },
  },
  {
    featureType: 'arterial',
    elementType: 'geometry',
    stylers: {
      visibility: 'off',
      curZoomRegionId: '0',
      curZoomRegion: '9,10',
      level: '10',
    },
  },
  {
    featureType: 'arterial',
    elementType: 'labels',
    stylers: {
      visibility: 'off',
      curZoomRegionId: '0',
      curZoomRegion: '9,10',
      level: '9',
    },
  },
  {
    featureType: 'arterial',
    elementType: 'labels',
    stylers: {
      visibility: 'off',
      curZoomRegionId: '0',
      curZoomRegion: '9,10',
      level: '10',
    },
  },
  {
    featureType: 'building',
    elementType: 'geometry.topfill',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'building',
    elementType: 'geometry.sidefill',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'building',
    elementType: 'geometry.stroke',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'road',
    elementType: 'labels.text.fill',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'road',
    elementType: 'labels.text.stroke',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'provincialway',
    elementType: 'labels.text.fill',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'cityhighway',
    elementType: 'labels.text.fill',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'arterial',
    elementType: 'labels.text.fill',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'provincialway',
    elementType: 'labels.text.stroke',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'cityhighway',
    elementType: 'labels.text.stroke',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'arterial',
    elementType: 'labels.text.stroke',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'local',
    elementType: 'labels',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'manmade',
    elementType: 'labels.text.fill',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'manmade',
    elementType: 'labels.text.stroke',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'subwaystation',
    elementType: 'geometry',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'transportationlabel',
    elementType: 'labels.icon',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'transportationlabel',
    elementType: 'labels',
    stylers: {
      visibility: 'off',
    },
  },
  {
    featureType: 'estate',
    elementType: 'geometry',
    stylers: {
      visibility: 'off',
    },
  },
];

export default styleJson2;
