import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import VykyUser from './vyky-user';
import VykyUserDetail from './vyky-user-detail';
import VykyUserUpdate from './vyky-user-update';
import VykyUserDeleteDialog from './vyky-user-delete-dialog';

const VykyUserRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<VykyUser />} />
    <Route path="new" element={<VykyUserUpdate />} />
    <Route path=":id">
      <Route index element={<VykyUserDetail />} />
      <Route path="edit" element={<VykyUserUpdate />} />
      <Route path="delete" element={<VykyUserDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VykyUserRoutes;
