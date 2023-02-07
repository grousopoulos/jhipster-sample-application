import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBunkerReceivedNoteLine } from 'app/shared/model/bunker-received-note-line.model';
import { getEntity, updateEntity, createEntity, reset } from './bunker-received-note-line.reducer';

export const BunkerReceivedNoteLineUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const bunkerReceivedNoteLineEntity = useAppSelector(state => state.bunkerReceivedNoteLine.entity);
  const loading = useAppSelector(state => state.bunkerReceivedNoteLine.loading);
  const updating = useAppSelector(state => state.bunkerReceivedNoteLine.updating);
  const updateSuccess = useAppSelector(state => state.bunkerReceivedNoteLine.updateSuccess);

  const handleClose = () => {
    navigate('/bunker-received-note-line');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...bunkerReceivedNoteLineEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...bunkerReceivedNoteLineEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="jhipsterSampleApplicationApp.bunkerReceivedNoteLine.home.createOrEditLabel"
            data-cy="BunkerReceivedNoteLineCreateUpdateHeading"
          >
            Create or edit a Bunker Received Note Line
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="bunker-received-note-line-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Quantity" id="bunker-received-note-line-quantity" name="quantity" data-cy="quantity" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/bunker-received-note-line" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default BunkerReceivedNoteLineUpdate;
