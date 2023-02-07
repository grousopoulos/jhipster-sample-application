import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVoyage } from 'app/shared/model/voyage.model';
import { getEntities as getVoyages } from 'app/entities/voyage/voyage.reducer';
import { IBunkerReceivedNote } from 'app/shared/model/bunker-received-note.model';
import { getEntity, updateEntity, createEntity, reset } from './bunker-received-note.reducer';

export const BunkerReceivedNoteUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const voyages = useAppSelector(state => state.voyage.entities);
  const bunkerReceivedNoteEntity = useAppSelector(state => state.bunkerReceivedNote.entity);
  const loading = useAppSelector(state => state.bunkerReceivedNote.loading);
  const updating = useAppSelector(state => state.bunkerReceivedNote.updating);
  const updateSuccess = useAppSelector(state => state.bunkerReceivedNote.updateSuccess);

  const handleClose = () => {
    navigate('/bunker-received-note');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getVoyages({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.documentDateAndTime = convertDateTimeToServer(values.documentDateAndTime);

    const entity = {
      ...bunkerReceivedNoteEntity,
      ...values,
      voyage: voyages.find(it => it.id.toString() === values.voyage.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          documentDateAndTime: displayDefaultDateTime(),
        }
      : {
          ...bunkerReceivedNoteEntity,
          documentDateAndTime: convertDateTimeFromServer(bunkerReceivedNoteEntity.documentDateAndTime),
          voyage: bunkerReceivedNoteEntity?.voyage?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterSampleApplicationApp.bunkerReceivedNote.home.createOrEditLabel" data-cy="BunkerReceivedNoteCreateUpdateHeading">
            Create or edit a Bunker Received Note
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
                <ValidatedField name="id" required readOnly id="bunker-received-note-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Document Date And Time"
                id="bunker-received-note-documentDateAndTime"
                name="documentDateAndTime"
                data-cy="documentDateAndTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Document Display Number"
                id="bunker-received-note-documentDisplayNumber"
                name="documentDisplayNumber"
                data-cy="documentDisplayNumber"
                type="text"
              />
              <ValidatedField id="bunker-received-note-voyage" name="voyage" data-cy="voyage" label="Voyage" type="select">
                <option value="" key="0" />
                {voyages
                  ? voyages.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/bunker-received-note" replace color="info">
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

export default BunkerReceivedNoteUpdate;
