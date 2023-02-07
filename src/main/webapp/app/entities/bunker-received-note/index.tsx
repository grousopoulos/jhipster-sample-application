import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BunkerReceivedNote from './bunker-received-note';
import BunkerReceivedNoteDetail from './bunker-received-note-detail';
import BunkerReceivedNoteUpdate from './bunker-received-note-update';
import BunkerReceivedNoteDeleteDialog from './bunker-received-note-delete-dialog';

const BunkerReceivedNoteRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BunkerReceivedNote />} />
    <Route path="new" element={<BunkerReceivedNoteUpdate />} />
    <Route path=":id">
      <Route index element={<BunkerReceivedNoteDetail />} />
      <Route path="edit" element={<BunkerReceivedNoteUpdate />} />
      <Route path="delete" element={<BunkerReceivedNoteDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BunkerReceivedNoteRoutes;
