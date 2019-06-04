import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TrasactionModalComponent } from './trasaction-modal.component';

describe('TrasactionModalComponent', () => {
  let component: TrasactionModalComponent;
  let fixture: ComponentFixture<TrasactionModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TrasactionModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TrasactionModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
