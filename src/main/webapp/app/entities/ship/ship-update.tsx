import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICountry } from 'app/shared/model/country.model';
import { getEntities as getCountries } from 'app/entities/country/country.reducer';
import { IFlag } from 'app/shared/model/flag.model';
import { getEntities as getFlags } from 'app/entities/flag/flag.reducer';
import { IShip } from 'app/shared/model/ship.model';
import { getEntity, updateEntity, createEntity, reset } from './ship.reducer';

export const ShipUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const countries = useAppSelector(state => state.country.entities);
  const flags = useAppSelector(state => state.flag.entities);
  const shipEntity = useAppSelector(state => state.ship.entity);
  const loading = useAppSelector(state => state.ship.loading);
  const updating = useAppSelector(state => state.ship.updating);
  const updateSuccess = useAppSelector(state => state.ship.updateSuccess);

  const handleClose = () => {
    navigate('/ship');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCountries({}));
    dispatch(getFlags({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...shipEntity,
      ...values,
      ownerCountry: countries.find(it => it.id.toString() === values.ownerCountry.toString()),
      flag: flags.find(it => it.id.toString() === values.flag.toString()),
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
          ...shipEntity,
          ownerCountry: shipEntity?.ownerCountry?.id,
          flag: shipEntity?.flag?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterSampleApplicationApp.ship.home.createOrEditLabel" data-cy="ShipCreateUpdateHeading">
            Create or edit a Ship
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="ship-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Name"
                id="ship-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Classification Society"
                id="ship-classificationSociety"
                name="classificationSociety"
                data-cy="classificationSociety"
                type="text"
              />
              <ValidatedField
                label="Ice Class Polar Code"
                id="ship-iceClassPolarCode"
                name="iceClassPolarCode"
                data-cy="iceClassPolarCode"
                type="text"
              />
              <ValidatedField
                label="Technical Efficiency Code"
                id="ship-technicalEfficiencyCode"
                name="technicalEfficiencyCode"
                data-cy="technicalEfficiencyCode"
                type="text"
              />
              <ValidatedField label="Ship Type" id="ship-shipType" name="shipType" data-cy="shipType" type="text" />
              <ValidatedField id="ship-ownerCountry" name="ownerCountry" data-cy="ownerCountry" label="Owner Country" type="select">
                <option value="" key="0" />
                {countries
                  ? countries.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="ship-flag" name="flag" data-cy="flag" label="Flag" type="select">
                <option value="" key="0" />
                {flags
                  ? flags.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/ship" replace color="info">
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

export default ShipUpdate;
