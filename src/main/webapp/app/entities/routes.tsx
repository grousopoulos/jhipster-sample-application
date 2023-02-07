import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Country from './country';
import Flag from './flag';
import Port from './port';
import ClasificationSociety from './clasification-society';
import Ship from './ship';
import Voyage from './voyage';
import FuelType from './fuel-type';
import BunkerReceivedNote from './bunker-received-note';
import BunkerReceivedNoteLine from './bunker-received-note-line';
import EventReport from './event-report';
import EventReportLine from './event-report-line';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="country/*" element={<Country />} />
        <Route path="flag/*" element={<Flag />} />
        <Route path="port/*" element={<Port />} />
        <Route path="clasification-society/*" element={<ClasificationSociety />} />
        <Route path="ship/*" element={<Ship />} />
        <Route path="voyage/*" element={<Voyage />} />
        <Route path="fuel-type/*" element={<FuelType />} />
        <Route path="bunker-received-note/*" element={<BunkerReceivedNote />} />
        <Route path="bunker-received-note-line/*" element={<BunkerReceivedNoteLine />} />
        <Route path="event-report/*" element={<EventReport />} />
        <Route path="event-report-line/*" element={<EventReportLine />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
