import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Thumb from './thumb';
import ThumbDetail from './thumb-detail';
import ThumbUpdate from './thumb-update';
import ThumbDeleteDialog from './thumb-delete-dialog';

const ThumbRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Thumb />} />
    <Route path="new" element={<ThumbUpdate />} />
    <Route path=":id">
      <Route index element={<ThumbDetail />} />
      <Route path="edit" element={<ThumbUpdate />} />
      <Route path="delete" element={<ThumbDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ThumbRoutes;
