import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './bunker-received-note.reducer';

export const BunkerReceivedNoteDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bunkerReceivedNoteEntity = useAppSelector(state => state.bunkerReceivedNote.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bunkerReceivedNoteDetailsHeading">Bunker Received Note</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{bunkerReceivedNoteEntity.id}</dd>
          <dt>
            <span id="documentDateAndTime">Document Date And Time</span>
          </dt>
          <dd>
            {bunkerReceivedNoteEntity.documentDateAndTime ? (
              <TextFormat value={bunkerReceivedNoteEntity.documentDateAndTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="documentDisplayNumber">Document Display Number</span>
          </dt>
          <dd>{bunkerReceivedNoteEntity.documentDisplayNumber}</dd>
          <dt>Voyage</dt>
          <dd>{bunkerReceivedNoteEntity.voyage ? bunkerReceivedNoteEntity.voyage.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/bunker-received-note" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bunker-received-note/${bunkerReceivedNoteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BunkerReceivedNoteDetail;
