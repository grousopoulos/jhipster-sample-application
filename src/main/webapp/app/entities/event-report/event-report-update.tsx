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
import { IEventReport } from 'app/shared/model/event-report.model';
import { getEntity, updateEntity, createEntity, reset } from './event-report.reducer';

export const EventReportUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const voyages = useAppSelector(state => state.voyage.entities);
  const eventReportEntity = useAppSelector(state => state.eventReport.entity);
  const loading = useAppSelector(state => state.eventReport.loading);
  const updating = useAppSelector(state => state.eventReport.updating);
  const updateSuccess = useAppSelector(state => state.eventReport.updateSuccess);

  const handleClose = () => {
    navigate('/event-report');
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
      ...eventReportEntity,
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
          ...eventReportEntity,
          documentDateAndTime: convertDateTimeFromServer(eventReportEntity.documentDateAndTime),
          voyage: eventReportEntity?.voyage?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterSampleApplicationApp.eventReport.home.createOrEditLabel" data-cy="EventReportCreateUpdateHeading">
            Create or edit a Event Report
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="event-report-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Document Date And Time"
                id="event-report-documentDateAndTime"
                name="documentDateAndTime"
                data-cy="documentDateAndTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField label="Speed Gps" id="event-report-speedGps" name="speedGps" data-cy="speedGps" type="text" />
              <ValidatedField
                label="Document Display Number"
                id="event-report-documentDisplayNumber"
                name="documentDisplayNumber"
                data-cy="documentDisplayNumber"
                type="text"
              />
              <ValidatedField id="event-report-voyage" name="voyage" data-cy="voyage" label="Voyage" type="select">
                <option value="" key="0" />
                {voyages
                  ? voyages.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/event-report" replace color="info">
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

export default EventReportUpdate;
