import candidate from 'app/entities/candidate/candidate.reducer';
import vykyUser from 'app/entities/vyky-user/vyky-user.reducer';
import avatar from 'app/entities/avatar/avatar.reducer';
import area from 'app/entities/area/area.reducer';
import topic from 'app/entities/topic/topic.reducer';
import post from 'app/entities/post/post.reducer';
import thumb from 'app/entities/thumb/thumb.reducer';
import message from 'app/entities/message/message.reducer';
import messageRecipientAttrs from 'app/entities/message-recipient-attrs/message-recipient-attrs.reducer';
import config from 'app/entities/config/config.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  candidate,
  vykyUser,
  avatar,
  area,
  topic,
  post,
  thumb,
  message,
  messageRecipientAttrs,
  config,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
