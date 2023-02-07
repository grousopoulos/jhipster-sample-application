import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Port from './port';
import PortDetail from './port-detail';
import PortUpdate from './port-update';
import PortDeleteDialog from './port-delete-dialog';

const PortRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Port />} />
    <Route path="new" element={<PortUpdate />} />
    <Route path=":id">
      <Route index element={<PortDetail />} />
      <Route path="edit" element={<PortUpdate />} />
      <Route path="delete" element={<PortDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PortRoutes;
