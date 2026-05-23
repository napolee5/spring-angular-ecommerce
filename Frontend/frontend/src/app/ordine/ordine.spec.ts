import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Ordine } from './ordine';

describe('Ordine', () => {
  let component: Ordine;
  let fixture: ComponentFixture<Ordine>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Ordine]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Ordine);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
