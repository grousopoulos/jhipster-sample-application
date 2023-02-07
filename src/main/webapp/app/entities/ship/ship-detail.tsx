import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './ship.reducer';

export const ShipDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const shipEntity = useAppSelector(state => state.ship.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="shipDetailsHeading">Ship</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{shipEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{shipEntity.name}</dd>
          <dt>
            <span id="classificationSociety">Classification Society</span>
          </dt>
          <dd>{shipEntity.classificationSociety}</dd>
          <dt>
            <span id="iceClassPolarCode">Ice Class Polar Code</span>
          </dt>
          <dd>{shipEntity.iceClassPolarCode}</dd>
          <dt>
            <span id="technicalEfficiencyCode">Technical Efficiency Code</span>
          </dt>
          <dd>{shipEntity.technicalEfficiencyCode}</dd>
          <dt>
            <span id="shipType">Ship Type</span>
          </dt>
          <dd>{shipEntity.shipType}</dd>
          <dt>Owner Country</dt>
          <dd>{shipEntity.ownerCountry ? shipEntity.ownerCountry.id : ''}</dd>
          <dt>Flag</dt>
          <dd>{shipEntity.flag ? shipEntity.flag.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/ship" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/ship/${shipEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ShipDetail;
