import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './event-report.reducer';

export const EventReportDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const eventReportEntity = useAppSelector(state => state.eventReport.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="eventReportDetailsHeading">Event Report</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{eventReportEntity.id}</dd>
          <dt>
            <span id="documentDateAndTime">Document Date And Time</span>
          </dt>
          <dd>
            {eventReportEntity.documentDateAndTime ? (
              <TextFormat value={eventReportEntity.documentDateAndTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="speedGps">Speed Gps</span>
          </dt>
          <dd>{eventReportEntity.speedGps}</dd>
          <dt>
            <span id="documentDisplayNumber">Document Display Number</span>
          </dt>
          <dd>{eventReportEntity.documentDisplayNumber}</dd>
          <dt>Voyage</dt>
          <dd>{eventReportEntity.voyage ? eventReportEntity.voyage.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/event-report" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/event-report/${eventReportEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EventReportDetail;
