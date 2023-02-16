export default {
  busNumbers: ['1','2','3','4','5'],
  ruleTypes: ['BUS_LOAD_TOO_HIGH', 'UNKNOWN_ARB_ID(Arbitration)', 'SIGNAL_CORRELATION_ERROR'],
  items: [
    { no : '1',  busNumber : '1',  ruleType : 'BUS_LOAD_TOO_HIGH',  expectedValue : '' },
    { no : '2',  busNumber : '2',  ruleType : 'BUS_LOAD_TOO_HIGH',  expectedValue : '' },
    { no : '3',  busNumber : '3',  ruleType : 'BUS_LOAD_TOO_HIGH',  expectedValue : '' },
    { no : '4',  busNumber : '4',  ruleType : 'BUS_LOAD_TOO_HIGH',  expectedValue : '' },
    { no : '5',  busNumber : '5',  ruleType : 'BUS_LOAD_TOO_HIGH',  expectedValue : '' },
    { no : '6',  busNumber : '1',  ruleType : 'SIGNAL_CORRELATION_ERROR',  expectedValue : '' },
    { no : '7',  busNumber : '2',  ruleType : 'SIGNAL_CORRELATION_ERROR',  expectedValue : '' },
    { no : '8',  busNumber : '3',  ruleType : 'SIGNAL_CORRELATION_ERROR',  expectedValue : '' },
  ]
}