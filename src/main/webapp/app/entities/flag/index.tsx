import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Flag from './flag';
import FlagDetail from './flag-detail';
import FlagUpdate from './flag-update';
import FlagDeleteDialog from './flag-delete-dialog';

const FlagRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Flag />} />
    <Route path="new" element={<FlagUpdate />} />
    <Route path=":id">
      <Route index element={<FlagDetail />} />
      <Route path="edit" element={<FlagUpdate />} />
      <Route path="delete" element={<FlagDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FlagRoutes;
