import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEventReport } from 'app/shared/model/event-report.model';
import { getEntities } from './event-report.reducer';

export const EventReport = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const eventReportList = useAppSelector(state => state.eventReport.entities);
  const loading = useAppSelector(state => state.eventReport.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="event-report-heading" data-cy="EventReportHeading">
        Event Reports
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/event-report/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Event Report
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {eventReportList && eventReportList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Document Date And Time</th>
                <th>Speed Gps</th>
                <th>Document Display Number</th>
                <th>Voyage</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {eventReportList.map((eventReport, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/event-report/${eventReport.id}`} color="link" size="sm">
                      {eventReport.id}
                    </Button>
                  </td>
                  <td>
                    {eventReport.documentDateAndTime ? (
                      <TextFormat type="date" value={eventReport.documentDateAndTime} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{eventReport.speedGps}</td>
                  <td>{eventReport.documentDisplayNumber}</td>
                  <td>{eventReport.voyage ? <Link to={`/voyage/${eventReport.voyage.id}`}>{eventReport.voyage.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/event-report/${eventReport.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/event-report/${eventReport.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/event-report/${eventReport.id}/delete`}
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
          !loading && <div className="alert alert-warning">No Event Reports found</div>
        )}
      </div>
    </div>
  );
};

export default EventReport;
