export default {
  getDaysLabel(date, length) {
    let day = date.date();
    let result = [];

    while(result.length < length) {
      result.push(`${day}`);

      if(day === 1 || result.length === length) {
        const month = date.format('MMMM');

        result[result.length-1] = `${month} ${day}`
      }

      day--;

      if(day < 1) {
        date = date.subtract(1, 'months');
        day = date.endOf('month').date();
      }
    }

    result = result.reverse();

    return result;
  }
}