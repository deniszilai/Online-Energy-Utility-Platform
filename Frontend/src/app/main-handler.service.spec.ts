import { TestBed } from '@angular/core/testing';

import { MainHandlerService } from './main-handler.service';

describe('MainHandlerService', () => {
  let service: MainHandlerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MainHandlerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
