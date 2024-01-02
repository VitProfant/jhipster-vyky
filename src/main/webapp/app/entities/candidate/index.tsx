import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Candidate from './candidate';
import CandidateDetail from './candidate-detail';
import CandidateUpdate from './candidate-update';
import CandidateDeleteDialog from './candidate-delete-dialog';

const CandidateRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Candidate />} />
    <Route path="new" element={<CandidateUpdate />} />
    <Route path=":id">
      <Route index element={<CandidateDetail />} />
      <Route path="edit" element={<CandidateUpdate />} />
      <Route path="delete" element={<CandidateDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CandidateRoutes;
