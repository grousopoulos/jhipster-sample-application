import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/country">
        Country
      </MenuItem>
      <MenuItem icon="asterisk" to="/flag">
        Flag
      </MenuItem>
      <MenuItem icon="asterisk" to="/port">
        Port
      </MenuItem>
      <MenuItem icon="asterisk" to="/clasification-society">
        Clasification Society
      </MenuItem>
      <MenuItem icon="asterisk" to="/ship">
        Ship
      </MenuItem>
      <MenuItem icon="asterisk" to="/voyage">
        Voyage
      </MenuItem>
      <MenuItem icon="asterisk" to="/fuel-type">
        Fuel Type
      </MenuItem>
      <MenuItem icon="asterisk" to="/bunker-received-note">
        Bunker Received Note
      </MenuItem>
      <MenuItem icon="asterisk" to="/bunker-received-note-line">
        Bunker Received Note Line
      </MenuItem>
      <MenuItem icon="asterisk" to="/event-report">
        Event Report
      </MenuItem>
      <MenuItem icon="asterisk" to="/event-report-line">
        Event Report Line
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
