import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ClasificationSociety from './clasification-society';
import ClasificationSocietyDetail from './clasification-society-detail';
import ClasificationSocietyUpdate from './clasification-society-update';
import ClasificationSocietyDeleteDialog from './clasification-society-delete-dialog';

const ClasificationSocietyRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ClasificationSociety />} />
    <Route path="new" element={<ClasificationSocietyUpdate />} />
    <Route path=":id">
      <Route index element={<ClasificationSocietyDetail />} />
      <Route path="edit" element={<ClasificationSocietyUpdate />} />
      <Route path="delete" element={<ClasificationSocietyDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ClasificationSocietyRoutes;
