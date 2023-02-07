import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import EventReport from './event-report';
import EventReportDetail from './event-report-detail';
import EventReportUpdate from './event-report-update';
import EventReportDeleteDialog from './event-report-delete-dialog';

const EventReportRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<EventReport />} />
    <Route path="new" element={<EventReportUpdate />} />
    <Route path=":id">
      <Route index element={<EventReportDetail />} />
      <Route path="edit" element={<EventReportUpdate />} />
      <Route path="delete" element={<EventReportDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EventReportRoutes;
