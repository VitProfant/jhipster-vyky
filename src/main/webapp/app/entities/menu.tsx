import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/candidate">
        <Translate contentKey="global.menu.entities.candidate" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/vyky-user">
        <Translate contentKey="global.menu.entities.vykyUser" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/avatar">
        <Translate contentKey="global.menu.entities.avatar" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/area">
        <Translate contentKey="global.menu.entities.area" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/topic">
        <Translate contentKey="global.menu.entities.topic" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/post">
        <Translate contentKey="global.menu.entities.post" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/thumb">
        <Translate contentKey="global.menu.entities.thumb" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/message">
        <Translate contentKey="global.menu.entities.message" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/message-recipient-attrs">
        <Translate contentKey="global.menu.entities.messageRecipientAttrs" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/config">
        <Translate contentKey="global.menu.entities.config" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
