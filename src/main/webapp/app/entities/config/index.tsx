import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Config from './config';
import ConfigDetail from './config-detail';
import ConfigUpdate from './config-update';
import ConfigDeleteDialog from './config-delete-dialog';

const ConfigRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Config />} />
    <Route path="new" element={<ConfigUpdate />} />
    <Route path=":id">
      <Route index element={<ConfigDetail />} />
      <Route path="edit" element={<ConfigUpdate />} />
      <Route path="delete" element={<ConfigDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ConfigRoutes;
