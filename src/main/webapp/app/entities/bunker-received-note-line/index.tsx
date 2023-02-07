import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BunkerReceivedNoteLine from './bunker-received-note-line';
import BunkerReceivedNoteLineDetail from './bunker-received-note-line-detail';
import BunkerReceivedNoteLineUpdate from './bunker-received-note-line-update';
import BunkerReceivedNoteLineDeleteDialog from './bunker-received-note-line-delete-dialog';

const BunkerReceivedNoteLineRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BunkerReceivedNoteLine />} />
    <Route path="new" element={<BunkerReceivedNoteLineUpdate />} />
    <Route path=":id">
      <Route index element={<BunkerReceivedNoteLineDetail />} />
      <Route path="edit" element={<BunkerReceivedNoteLineUpdate />} />
      <Route path="delete" element={<BunkerReceivedNoteLineDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BunkerReceivedNoteLineRoutes;
