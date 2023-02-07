import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFuelType } from 'app/shared/model/fuel-type.model';
import { getEntities as getFuelTypes } from 'app/entities/fuel-type/fuel-type.reducer';
import { IEventReportLine } from 'app/shared/model/event-report-line.model';
import { UnitOfMeasure } from 'app/shared/model/enumerations/unit-of-measure.model';
import { getEntity, updateEntity, createEntity, reset } from './event-report-line.reducer';

export const EventReportLineUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const fuelTypes = useAppSelector(state => state.fuelType.entities);
  const eventReportLineEntity = useAppSelector(state => state.eventReportLine.entity);
  const loading = useAppSelector(state => state.eventReportLine.loading);
  const updating = useAppSelector(state => state.eventReportLine.updating);
  const updateSuccess = useAppSelector(state => state.eventReportLine.updateSuccess);
  const unitOfMeasureValues = Object.keys(UnitOfMeasure);

  const handleClose = () => {
    navigate('/event-report-line');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getFuelTypes({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...eventReportLineEntity,
      ...values,
      fuelType: fuelTypes.find(it => it.id.toString() === values.fuelType.toString()),
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
          unitOfMeasure: 'M_TONNES',
          ...eventReportLineEntity,
          fuelType: eventReportLineEntity?.fuelType?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterSampleApplicationApp.eventReportLine.home.createOrEditLabel" data-cy="EventReportLineCreateUpdateHeading">
            Create or edit a Event Report Line
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
                <ValidatedField name="id" required readOnly id="event-report-line-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Quantity" id="event-report-line-quantity" name="quantity" data-cy="quantity" type="text" />
              <ValidatedField
                label="Unit Of Measure"
                id="event-report-line-unitOfMeasure"
                name="unitOfMeasure"
                data-cy="unitOfMeasure"
                type="select"
              >
                {unitOfMeasureValues.map(unitOfMeasure => (
                  <option value={unitOfMeasure} key={unitOfMeasure}>
                    {unitOfMeasure}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField id="event-report-line-fuelType" name="fuelType" data-cy="fuelType" label="Fuel Type" type="select">
                <option value="" key="0" />
                {fuelTypes
                  ? fuelTypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/event-report-line" replace color="info">
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

export default EventReportLineUpdate;
