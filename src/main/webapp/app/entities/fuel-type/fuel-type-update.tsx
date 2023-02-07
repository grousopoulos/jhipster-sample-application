import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFuelType } from 'app/shared/model/fuel-type.model';
import { FuelTypeCode } from 'app/shared/model/enumerations/fuel-type-code.model';
import { getEntity, updateEntity, createEntity, reset } from './fuel-type.reducer';

export const FuelTypeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const fuelTypeEntity = useAppSelector(state => state.fuelType.entity);
  const loading = useAppSelector(state => state.fuelType.loading);
  const updating = useAppSelector(state => state.fuelType.updating);
  const updateSuccess = useAppSelector(state => state.fuelType.updateSuccess);
  const fuelTypeCodeValues = Object.keys(FuelTypeCode);

  const handleClose = () => {
    navigate('/fuel-type');
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
      ...fuelTypeEntity,
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
          fuelTypeCode: 'MDO',
          ...fuelTypeEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterSampleApplicationApp.fuelType.home.createOrEditLabel" data-cy="FuelTypeCreateUpdateHeading">
            Create or edit a Fuel Type
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="fuel-type-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Name"
                id="fuel-type-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField label="Fuel Type Code" id="fuel-type-fuelTypeCode" name="fuelTypeCode" data-cy="fuelTypeCode" type="select">
                {fuelTypeCodeValues.map(fuelTypeCode => (
                  <option value={fuelTypeCode} key={fuelTypeCode}>
                    {fuelTypeCode}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label="Carbon Factory"
                id="fuel-type-carbonFactory"
                name="carbonFactory"
                data-cy="carbonFactory"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/fuel-type" replace color="info">
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

export default FuelTypeUpdate;
