import country from 'app/entities/country/country.reducer';
import flag from 'app/entities/flag/flag.reducer';
import port from 'app/entities/port/port.reducer';
import clasificationSociety from 'app/entities/clasification-society/clasification-society.reducer';
import ship from 'app/entities/ship/ship.reducer';
import voyage from 'app/entities/voyage/voyage.reducer';
import fuelType from 'app/entities/fuel-type/fuel-type.reducer';
import bunkerReceivedNote from 'app/entities/bunker-received-note/bunker-received-note.reducer';
import bunkerReceivedNoteLine from 'app/entities/bunker-received-note-line/bunker-received-note-line.reducer';
import eventReport from 'app/entities/event-report/event-report.reducer';
import eventReportLine from 'app/entities/event-report-line/event-report-line.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  country,
  flag,
  port,
  clasificationSociety,
  ship,
  voyage,
  fuelType,
  bunkerReceivedNote,
  bunkerReceivedNoteLine,
  eventReport,
  eventReportLine,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
