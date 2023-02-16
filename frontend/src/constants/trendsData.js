function getRandomIntArr(size, min, max) {
  const result = [];
  for (let i = 1; i <= size; i++) {
    result.push(
      Math.floor(Math.random() * (max - min + 1)) + min
    )
  }
  return result;
}

export default {
  overAll: {
    lineData: getRandomIntArr(31, 0, 700),
    barData: getRandomIntArr(31, 700, 1400)
  },
  region: {
    lineData: getRandomIntArr(31, 0, 700),
    barData: getRandomIntArr(31, 700, 1400)
  },
  vehicle: {
    lineData: getRandomIntArr(62, 100, 350),
    barData: getRandomIntArr(62,350,700),
  },
  parts: getRandomIntArr(49, 0, 50),
  singularity: {
    lineData: getRandomIntArr(62, 100, 350),
    barData: getRandomIntArr(62, 350, 700)
  }
}