import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Voyage from './voyage';
import VoyageDetail from './voyage-detail';
import VoyageUpdate from './voyage-update';
import VoyageDeleteDialog from './voyage-delete-dialog';

const VoyageRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Voyage />} />
    <Route path="new" element={<VoyageUpdate />} />
    <Route path=":id">
      <Route index element={<VoyageDetail />} />
      <Route path="edit" element={<VoyageUpdate />} />
      <Route path="delete" element={<VoyageDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VoyageRoutes;
