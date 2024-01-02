import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Candidate from './candidate';
import VykyUser from './vyky-user';
import Avatar from './avatar';
import Area from './area';
import Topic from './topic';
import Post from './post';
import Thumb from './thumb';
import Message from './message';
import MessageRecipientAttrs from './message-recipient-attrs';
import Config from './config';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="candidate/*" element={<Candidate />} />
        <Route path="vyky-user/*" element={<VykyUser />} />
        <Route path="avatar/*" element={<Avatar />} />
        <Route path="area/*" element={<Area />} />
        <Route path="topic/*" element={<Topic />} />
        <Route path="post/*" element={<Post />} />
        <Route path="thumb/*" element={<Thumb />} />
        <Route path="message/*" element={<Message />} />
        <Route path="message-recipient-attrs/*" element={<MessageRecipientAttrs />} />
        <Route path="config/*" element={<Config />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
