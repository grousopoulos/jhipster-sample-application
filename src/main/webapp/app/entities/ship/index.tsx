import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Ship from './ship';
import ShipDetail from './ship-detail';
import ShipUpdate from './ship-update';
import ShipDeleteDialog from './ship-delete-dialog';

const ShipRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Ship />} />
    <Route path="new" element={<ShipUpdate />} />
    <Route path=":id">
      <Route index element={<ShipDetail />} />
      <Route path="edit" element={<ShipUpdate />} />
      <Route path="delete" element={<ShipDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ShipRoutes;
