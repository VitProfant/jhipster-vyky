import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import MessageRecipientAttrs from './message-recipient-attrs';
import MessageRecipientAttrsDetail from './message-recipient-attrs-detail';
import MessageRecipientAttrsUpdate from './message-recipient-attrs-update';
import MessageRecipientAttrsDeleteDialog from './message-recipient-attrs-delete-dialog';

const MessageRecipientAttrsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<MessageRecipientAttrs />} />
    <Route path="new" element={<MessageRecipientAttrsUpdate />} />
    <Route path=":id">
      <Route index element={<MessageRecipientAttrsDetail />} />
      <Route path="edit" element={<MessageRecipientAttrsUpdate />} />
      <Route path="delete" element={<MessageRecipientAttrsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MessageRecipientAttrsRoutes;
