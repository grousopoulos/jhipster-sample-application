import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './event-report-line.reducer';

export const EventReportLineDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const eventReportLineEntity = useAppSelector(state => state.eventReportLine.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="eventReportLineDetailsHeading">Event Report Line</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{eventReportLineEntity.id}</dd>
          <dt>
            <span id="quantity">Quantity</span>
          </dt>
          <dd>{eventReportLineEntity.quantity}</dd>
          <dt>
            <span id="unitOfMeasure">Unit Of Measure</span>
          </dt>
          <dd>{eventReportLineEntity.unitOfMeasure}</dd>
          <dt>Fuel Type</dt>
          <dd>{eventReportLineEntity.fuelType ? eventReportLineEntity.fuelType.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/event-report-line" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/event-report-line/${eventReportLineEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EventReportLineDetail;
