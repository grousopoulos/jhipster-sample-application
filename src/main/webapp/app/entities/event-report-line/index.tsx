import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import EventReportLine from './event-report-line';
import EventReportLineDetail from './event-report-line-detail';
import EventReportLineUpdate from './event-report-line-update';
import EventReportLineDeleteDialog from './event-report-line-delete-dialog';

const EventReportLineRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<EventReportLine />} />
    <Route path="new" element={<EventReportLineUpdate />} />
    <Route path=":id">
      <Route index element={<EventReportLineDetail />} />
      <Route path="edit" element={<EventReportLineUpdate />} />
      <Route path="delete" element={<EventReportLineDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EventReportLineRoutes;
