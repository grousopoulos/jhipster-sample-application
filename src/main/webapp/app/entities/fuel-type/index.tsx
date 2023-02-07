import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FuelType from './fuel-type';
import FuelTypeDetail from './fuel-type-detail';
import FuelTypeUpdate from './fuel-type-update';
import FuelTypeDeleteDialog from './fuel-type-delete-dialog';

const FuelTypeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FuelType />} />
    <Route path="new" element={<FuelTypeUpdate />} />
    <Route path=":id">
      <Route index element={<FuelTypeDetail />} />
      <Route path="edit" element={<FuelTypeUpdate />} />
      <Route path="delete" element={<FuelTypeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FuelTypeRoutes;
