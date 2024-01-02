import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Avatar from './avatar';
import AvatarDetail from './avatar-detail';
import AvatarUpdate from './avatar-update';
import AvatarDeleteDialog from './avatar-delete-dialog';

const AvatarRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Avatar />} />
    <Route path="new" element={<AvatarUpdate />} />
    <Route path=":id">
      <Route index element={<AvatarDetail />} />
      <Route path="edit" element={<AvatarUpdate />} />
      <Route path="delete" element={<AvatarDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AvatarRoutes;
