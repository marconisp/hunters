import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IUser1, User1 } from '../user-1.model';
import { User1Service } from '../service/user-1.service';

@Component({
  selector: 'jhi-user-1-update',
  templateUrl: './user-1-update.component.html',
})
export class User1UpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    firstName: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(20)]],
    lastName: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(40)]],
    email: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(60)]],
  });

  constructor(protected user1Service: User1Service, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ user1 }) => {
      this.updateForm(user1);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const user1 = this.createFromForm();
    if (user1.id !== undefined) {
      this.subscribeToSaveResponse(this.user1Service.update(user1));
    } else {
      this.subscribeToSaveResponse(this.user1Service.create(user1));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUser1>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(user1: IUser1): void {
    this.editForm.patchValue({
      id: user1.id,
      firstName: user1.firstName,
      lastName: user1.lastName,
      email: user1.email,
    });
  }

  protected createFromForm(): IUser1 {
    return {
      ...new User1(),
      id: this.editForm.get(['id'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      email: this.editForm.get(['email'])!.value,
    };
  }
}
