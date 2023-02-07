import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEventReportLine } from 'app/shared/model/event-report-line.model';
import { getEntities } from './event-report-line.reducer';

export const EventReportLine = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const eventReportLineList = useAppSelector(state => state.eventReportLine.entities);
  const loading = useAppSelector(state => state.eventReportLine.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="event-report-line-heading" data-cy="EventReportLineHeading">
        Event Report Lines
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/event-report-line/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Event Report Line
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {eventReportLineList && eventReportLineList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Quantity</th>
                <th>Unit Of Measure</th>
                <th>Fuel Type</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {eventReportLineList.map((eventReportLine, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/event-report-line/${eventReportLine.id}`} color="link" size="sm">
                      {eventReportLine.id}
                    </Button>
                  </td>
                  <td>{eventReportLine.quantity}</td>
                  <td>{eventReportLine.unitOfMeasure}</td>
                  <td>
                    {eventReportLine.fuelType ? (
                      <Link to={`/fuel-type/${eventReportLine.fuelType.id}`}>{eventReportLine.fuelType.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/event-report-line/${eventReportLine.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/event-report-line/${eventReportLine.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/event-report-line/${eventReportLine.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Event Report Lines found</div>
        )}
      </div>
    </div>
  );
};

export default EventReportLine;
